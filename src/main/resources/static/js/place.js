window.onload = function () {

    const { x, y, mapId } = mapDto;

    console.log('mapId:', mapId);

    fetch(`/map/place/json?id=${mapId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const lat = y || 37.65146111;
            const lng = x || 127.0583889;

            console.log('위도:', lat);
            console.log('경도:', lng);

            const mapContainer = document.getElementById('place-map');
            const mapOption = {
                center: new kakao.maps.LatLng(lat, lng),
                level: 3
            };

            const map = new kakao.maps.Map(mapContainer, mapOption);

            const markerPosition = new kakao.maps.LatLng(lat, lng);
            const marker = new kakao.maps.Marker({
                position: markerPosition
            });

            marker.setMap(map);

            const infowindow = new kakao.maps.InfoWindow({
                content: `<div style="padding:5px;">${data.placeName}</div>`
            });

            kakao.maps.event.addListener(marker, 'click', function () {
                infowindow.open(map, marker);
            });

            // 지도타입 컨트롤을 추가합니다
            const mapTypeControl = new kakao.maps.MapTypeControl();
            map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

            // 지도 확대, 축소 컨트롤을 생성합니다
            const zoomControl = new kakao.maps.ZoomControl();
            map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

         })
        .catch(error => {
            console.error('Fetch error:', error);
        });
};

const httpRequest = function (url, option) {
    return fetch(url, option).then(response => response.json());
}