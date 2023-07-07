function loadMusicList() {
    fetch('/api/music')
        .then(response => response.json())
        .then(musicList => renderMusicList(musicList))
        .catch(error => {
            showErrorMessage(error)
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
        deleteButton.addEventListener('click', _ => {
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
                throw new Error();
            }
            return response.blob();
        })
        .then(blob => {
            audioPlayer.src = URL.createObjectURL(blob);
            audioPlayer.load();
            audioPlayer.play();
        })
        .catch(error => {
            showErrorMessage()
        });
}
function deleteMusic(fileName) {
    var encodedSongName = encodeURIComponent(fileName);
    fetch(`api/music/${encodedSongName}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
            loadMusicList()
        })
        .catch(error => {
            showErrorMessage();
        });
}
function selectSong() {
    var songInput = document.getElementById("songInput");
    songInput.click();
}
document.getElementById("songInput").addEventListener("change", event => {
    var file = event.target.files[0];
    var formData = new FormData();
    formData.append("file", file);

    fetch("/api/music/upload", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error();
            }
            loadMusicList()
        })
        .catch(error => {
            showErrorMessage()
        });
});
function showErrorMessage() {
    alert('Ups! Ocurrió un error');
}
document.addEventListener("DOMContentLoaded", _ => {
    loadMusicList();
});
