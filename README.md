# AlkemyPreAceleracion
Challenge para la Aceleración de Alkemy:<br />
El formato de fecha para la creacion de peliculas es dd/MM/yyyy y se muestra de la misma manera.<br /> 
Agrege metodos de asociacion y desasociacion en el controlador de Peliculas, si desean asociar un personaje con una pelicula deben hacerlo desde esos endpoints, 
no estan implementandos los metodos de asociacion desde el endpoint de Personajes. <br />
Para enviar los mails de registros a traves de sendgrid se deben configurar las variables desde el archivo app.properties, tanto el apikey como el mail sender.<br />
Lamentablemente no llegue a testear los filtros.. Y se que no contempla el caso en que se dupliquen las asociaciones, tampoco llegue a implementar los test 
