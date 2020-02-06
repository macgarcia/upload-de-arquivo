function imprimirImagem(id) {
	var xhr = new XMLHttpRequest();
	xhr.open( "GET", "http://localhost:8080/arquivo/arq/" + id, true );
	//xhr.responseType = "arraybuffer";
	xhr.onload = function( e ) {
		if (this.status == 200) {
			var o = JSON.parse(this.responseText);
			var imagem = document.getElementById("foto");
			imagem.src = "data:image/png;base64," + o.arquivo;
			imagem.width = 400;
			imagem.height = 200;
		}
	};
	xhr.send();
}