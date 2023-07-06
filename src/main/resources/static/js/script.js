function loadMusicList() {
    fetch('/api/music')
        .then(response => response.json())
        .then(musicList => renderMusicList(musicList))
        .catch(error => {
            const errorMessageElement = document.getElementById('error-message');
            errorMessageElement.innerHTML = 'Error al obtener la lista de música';
            errorMessageElement.classList.add('error-message');
        });
}
function renderMusicList(musicList) {
    var musicListElement = document.getElementById('musicList');
    musicListElement.innerHTML = '';
    musicList.forEach(function (music) {
        var listItem = document.createElement('li');
        var link = document.createElement('a');
        link.href = 'javascript:playMusic("' + music + '")';
        link.innerText = music;
        listItem.appendChild(link);
        var deleteButton = document.createElement('button');
        deleteButton.innerText = '-';
        deleteButton.addEventListener('click', function () {
            deleteMusic(music);
        });

        listItem.appendChild(deleteButton);
        musicListElement.appendChild(listItem);
    });
}
function playMusic(fileName) {
    var audioPlayer = document.getElementById("audioPlayer");
    var encodedSongName = encodeURIComponent(fileName);
    fetch('/api/music/play/' + encodedSongName)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el archivo de música');
            }
            return response.blob();
        })
        .then(blob => {
            audioPlayer.src = URL.createObjectURL(blob);
            audioPlayer.load();
            audioPlayer.play();
        })
        .catch(error => {
            const errorMessageElement = document.getElementById('error-message');
            errorMessageElement.innerHTML = error.message;
            errorMessageElement.classList.add('error-message');
        });
}
function deleteMusic(fileName) {
    var encodedSongName = encodeURIComponent(fileName);
    fetch(`api/music/${encodedSongName}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar el archivo de música');
            }
            loadMusicList()
        })
        .catch(error => {
            const errorMessageElement = document.getElementById('error-message');
            errorMessageElement.innerHTML = error.message;
            errorMessageElement.classList.add('error-message');
        });
}
function selectSong() {
    var songInput = document.getElementById("songInput");
    songInput.click();
}
document.getElementById("songInput").addEventListener("change", function (event) {
    var file = event.target.files[0];
    var formData = new FormData();
    formData.append("file", file);

    fetch("/api/music/upload", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al guardar el archivo de música');
            }
            loadMusicList()
        })
        .catch(error => {
            const errorMessageElement = document.getElementById('error-message');
            errorMessageElement.innerHTML = error.message;
            errorMessageElement.classList.add('error-message');
        });
});
document.addEventListener("DOMContentLoaded", function () {
    loadMusicList();
});
