/**
 * Created by Михаил on 01.12.2016.
 */

var retro = [
    {elementType: 'geometry', stylers: [{color: '#ebe3cd'}]},
    {elementType: 'labels.text.fill', stylers: [{color: '#523735'}]},
    {elementType: 'labels.text.stroke', stylers: [{color: '#f5f1e6'}]},
    {
        featureType: 'administrative',
        elementType: 'geometry.stroke',
        stylers: [{color: '#c9b2a6'}]
    },
    {
        featureType: 'administrative.land_parcel',
        elementType: 'geometry.stroke',
        stylers: [{color: '#dcd2be'}]
    },
    {
        featureType: 'administrative.land_parcel',
        elementType: 'labels.text.fill',
        stylers: [{color: '#ae9e90'}]
    },
    {
        featureType: 'landscape.natural',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
    },
    {
        featureType: 'poi',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
    },
    {
        featureType: 'poi',
        elementType: 'labels.text.fill',
        stylers: [{color: '#93817c'}]
    },
    {
        featureType: 'poi.park',
        elementType: 'geometry.fill',
        stylers: [{color: '#a5b076'}]
    },
    {
        featureType: 'poi.park',
        elementType: 'labels.text.fill',
        stylers: [{color: '#447530'}]
    },
    {
        featureType: 'road',
        elementType: 'geometry',
        stylers: [{color: '#f5f1e6'}]
    },
    {
        featureType: 'road.arterial',
        elementType: 'geometry',
        stylers: [{color: '#fdfcf8'}]
    },
    {
        featureType: 'road.highway',
        elementType: 'geometry',
        stylers: [{color: '#f8c967'}]
    },
    {
        featureType: 'road.highway',
        elementType: 'geometry.stroke',
        stylers: [{color: '#e9bc62'}]
    },
    {
        featureType: 'road.highway.controlled_access',
        elementType: 'geometry',
        stylers: [{color: '#e98d58'}]
    },
    {
        featureType: 'road.highway.controlled_access',
        elementType: 'geometry.stroke',
        stylers: [{color: '#db8555'}]
    },
    {
        featureType: 'road.local',
        elementType: 'labels.text.fill',
        stylers: [{color: '#806b63'}]
    },
    {
        featureType: 'transit.line',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
    },
    {
        featureType: 'transit.line',
        elementType: 'labels.text.fill',
        stylers: [{color: '#8f7d77'}]
    },
    {
        featureType: 'transit.line',
        elementType: 'labels.text.stroke',
        stylers: [{color: '#ebe3cd'}]
    },
    {
        featureType: 'transit.station',
        elementType: 'geometry',
        stylers: [{color: '#dfd2ae'}]
    },
    {
        featureType: 'water',
        elementType: 'geometry.fill',
        stylers: [{color: '#b9d3c2'}]
    },
    {
        featureType: 'water',
        elementType: 'labels.text.fill',
        stylers: [{color: '#92998d'}]
    }
];

var marker;
var infoWindow;

function addLocation(lat, lng){
    //alert(lat.toString() + " " + lng.toString())
    $('#add_point_form').show(500);
    $('#lat').val(lat);
    $('#lon').val(lng);

    infoWindow.close();
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
}

function initMap() {
    // Create a map object and specify the DOM element for display.
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 55.814331, lng: 49.103508},
        scrollwheel: true,
        zoom: 10,
        mapTypeControlOptions: {
            mapTypeIds: [
                google.maps.MapTypeId.ROADMAP,
                google.maps.MapTypeId.SATELLITE
            ],
            position: google.maps.ControlPosition.BOTTOM_LEFT
        }
    });

    addUserLocationOnMap(map);

    map.setOptions({styles: retro});

    map.addListener('click', function (e) {

        if (marker != null) {
            marker.setMap(null);
            marker = null
        }

        marker = new google.maps.Marker({
            map: map,
            // Define the place with a location, and a query string.
            position: e.latLng,
            // Attributions help users find your site again.
            attribution: {
                source: 'Google Maps JavaScript API',
                webUrl: 'https://developers.google.com/maps/'
            },
            draggable: true
        });

        // Construct a new InfoWindow.
        infoWindow = new google.maps.InfoWindow({
            content: '<button onclick="addLocation' + e.latLng.toString() + '" class="button" style="margin: auto">Добавить точку</button>' +
            'Координаты:' +
            '<br/>' + e.latLng
        });

        // Opens the InfoWindow when marker is clicked.
        marker.addListener('click', function () {
            map.panTo(e.latLng);
            infoWindow.open(map, marker);
        });

        map.panTo(e.latLng);
        infoWindow.open(map, marker);
    });

    function addUserLocationOnMap(map){
        // Construct a new InfoWindow.

        // Try HTML5 geolocation.
        var goldStar = {
            path: 'M 125,5 155,90 245,90 175,145 200,230 125,180 50,230 75,145 5,90 95,90 z',
            fillColor: 'yellow',
            fillOpacity: 0.8,
            scale: 0.1,
            strokeColor: 'red',
            strokeWeight: 3
        };

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };

                var markerUserLocation = new google.maps.Marker({
                    map: map,
                    // Define the place with a location, and a query string.
                    place: {
                        location: pos,
                        query: 'Казань'

                    },
                    // Attributions help users find your site again.
                    attribution: {
                        source: 'Google Maps JavaScript API',
                        webUrl: 'https://developers.google.com/maps/'
                    },

                    label: "Ты",
                    icon: goldStar,
                    animation: google.maps.Animation.DROP
                });

                var infoWindow = new google.maps.InfoWindow({
                    content: 'Ваша позиция:<br/>' +
                    '<button onclick="addLocation(' + pos.lat + ", " + pos.lng + ')" class="button" style="margin: auto">Добавить точку</button>' +
                    'Координаты:' +
                    '<br/>' + pos.lat + " " + pos.lng +
                    '<br/>'
                });

                // Opens the InfoWindow when marker is clicked.
                markerUserLocation.addListener('click', function () {
                    map.panTo(pos);
                    infoWindow.open(map, markerUserLocation);
                });

                infoWindow.setPosition(pos);

            }, function() {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    }
}

$('#add_point_form').hide();






