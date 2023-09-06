package ru.netology.nmedia.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.ui_view.ViewProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentMapBinding
import ru.netology.nmedia.databinding.PlaceBinding //Импортирует класс PlaceBinding из пакета ru.netology.nmedia.databinding
import ru.netology.nmedia.dto.Coordinates
import ru.netology.nmedia.viewmodel.EventViewModel
import ru.netology.nmedia.viewmodel.MapViewModel
import ru.netology.nmedia.viewmodel.PostViewModel

class MapFragment : Fragment() { //Определяет класс MapFragment, который является подклассом класса Fragment
    //Создаёт экземпляр класса MapViewModel с помощью функции activityViewModels() и присваивает его переменной mapViewModel
    private val mapViewModel by activityViewModels<MapViewModel>()
    private val postViewModel by activityViewModels<PostViewModel>()
    private val eventViewModel by activityViewModels<EventViewModel>()
    //Объявляет переменную mapView типа MapView и присваивает ей значение null
    private var mapView: MapView? = null
    //Объявляет переменную userLocationLayer типа UserLocationLayer без инициализации
    private lateinit var userLocationLayer: UserLocationLayer

    private val mapTapListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) = Unit

        override fun onMapLongTap(map: Map, point: Point) {
            val latitude = point.latitude.toString().substring(0, 9)
            val longitude = point.longitude.toString().substring(0, 9)
            MaterialAlertDialogBuilder(requireContext())
                .create().apply {
                    setTitle(R.string.coordinates)
                    setMessage(getString(R.string.latitude_and_longitude, latitude, longitude))
                    setIcon(R.drawable.place)
                    setButton(
                        AlertDialog.BUTTON_POSITIVE,
                        getString(android.R.string.ok)
                    ) { _, _ ->
                        mapViewModel.setCoordinates(
                            Coordinates(
                                latitude,
                                longitude
                            )
                        )
                    }
                    setButton(
                        AlertDialog.BUTTON_NEGATIVE,
                        getString(android.R.string.cancel)
                    ) { _, _ ->
                        dismiss()
                    }
                }.show()
        }
    }

    private val locationObjectListener = object : UserLocationObjectListener {
        override fun onObjectAdded(userLocationView: UserLocationView) = Unit

        override fun onObjectRemoved(view: UserLocationView) = Unit

        override fun onObjectUpdated(view: UserLocationView, event: ObjectEvent) {
            userLocationLayer.cameraPosition()?.target?.let {
                mapView?.map?.move(CameraPosition(it, 10F, 0F, 0F))
            }
            userLocationLayer.setObjectListener(null)
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    userLocationLayer.isVisible = true
                    userLocationLayer.isHeadingEnabled = false
                    userLocationLayer.cameraPosition()?.target?.also {
                        val map = mapView?.map ?: return@registerForActivityResult
                        val cameraPosition = map.cameraPosition
                        map.move(
                            CameraPosition(
                                it,
                                cameraPosition.zoom,
                                cameraPosition.azimuth,
                                cameraPosition.tilt,
                            )
                        )
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.permission_request),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapBinding.inflate(inflater, container, false)

        mapView = binding.mapView.apply {
            userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapWindow)
            if (requireActivity()
                    .checkSelfPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                userLocationLayer.isVisible = true
                userLocationLayer.isHeadingEnabled = false
            }

            map.addInputListener(mapTapListener)
            //Создаёт коллекцию объектов на карте
            val collection = map.mapObjects.addCollection()
            //Запускает цикл, который будет повторяться при изменении жизненного цикла связанного с View
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    //Подписываемся на Flow, который предоставляет местоположение
                    mapViewModel.place.collectLatest {
                        collection.clear() //Очищает коллекцию объектов на карте
                        //Проверяет, что местоположение не равно null
                        it?.let { coordinates ->
                            //Создаёт экземпляр класса PlaceBinding с помощью метода inflate() и передаёт ему layoutInflater
                            val placeBinding = PlaceBinding.inflate(layoutInflater)
                            // Добавляем метку на карту с координатами из местоположения и используя ViewProvider для отображения метки на карте
                            collection.addPlacemark(
                                Point(coordinates.lat.toDouble(), coordinates.long.toDouble()),
                                ViewProvider(placeBinding.root)
                            )
                        }
                    }
                }
            }

            // go to the point
            val arguments = arguments
            if (arguments != null &&
                arguments.containsKey(LAT_KEY) &&
                arguments.containsKey(LONG_KEY)
            ) {
                mapViewModel.clearPlace()
                map.removeInputListener(mapTapListener)
                val latitude = arguments.getDouble(LAT_KEY)
                val longitude = arguments.getDouble(LONG_KEY)
                mapViewModel.setPlace(Coordinates(latitude.toString(), longitude.toString()))
                val cameraPosition = map.cameraPosition
                map.move(
                    CameraPosition(
                        Point(latitude, longitude),
                        10F,
                        cameraPosition.azimuth,
                        cameraPosition.tilt,
                    )
                )
                arguments.remove(LAT_KEY)
                arguments.remove(LONG_KEY)
            } else {
                mapViewModel.clearPlace()
                userLocationLayer.setObjectListener(locationObjectListener)
            }
        }

        // Buttons click listeners
        binding.fabLocation.setOnClickListener {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding.plus.setOnClickListener {
            binding.mapView.map.move(
                CameraPosition(
                    binding.mapView.map.cameraPosition.target,
                    binding.mapView.map.cameraPosition.zoom + 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 0.3F),
                null
            )
        }

        binding.minus.setOnClickListener {
            binding.mapView.map.move(
                CameraPosition(
                    binding.mapView.map.cameraPosition.target,
                    binding.mapView.map.cameraPosition.zoom - 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 0.3F),
                null,
            )
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }


        lifecycleScope.launch {
            //Повторяется при изменении жизненного цикла связанного с View
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //Подписываемся на Flow, который предоставляет координаты
                mapViewModel.coordinates.collectLatest { coords ->
                    //Проверяет, что координаты не равны null
                    coords?.let {
                        //Проверяет тип элемента, переданного в аргументах фрагмента
                        when (requireArguments().getString(ITEM_TYPE)) {
                            //Если тип элемента - пост, вызываем метод addCoordinates() у экземпляра
                            //класса PostViewModel и передаем ему координаты
                            ItemType.POST.name -> {
                                postViewModel.addCoordinates(coords)
                            }
                            //Если тип элемента - событие, вызываем метод addCoordinates() у экземпляра
                            //класса EventViewModel и передаем ему координаты
                            ItemType.EVENT.name -> {
                                eventViewModel.addCoordinates(coords)
                            }
                        }
                        // Переходим на предыдущий экран
                        findNavController().navigateUp()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView = null
    }


    companion object {
        const val LAT_KEY = "LAT_KEY"
        const val LONG_KEY = "LONG_KEY"
        const val ITEM_TYPE = "ITEM_TYPE"

        enum class ItemType {
            POST, EVENT
        }
    }
}