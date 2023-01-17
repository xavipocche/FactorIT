# Market Cart
## REST API created with Java ♨ and Spring Boot 🍃

# English 
The project simulates a shopping cart, where products can also be added and removed from it, users can be created and deleted, these are assigned to carts and with an endpoint the cart payment can be simulated. The function verifies that the user has a sufficient balance to pay the total sum of the prices of the products in the cart; otherwise, an exception is reported. All endpoints are documented with the swagger tool, to access the tool you must go to http://localhost:8080/swagger-ui/index.html.

Inside source packages there is a folder called FactorITtest where you can find a postman collection with all the endpoints that the API contains, also there are 2 SQL files where you can insert products or directly create the tables with some data that I added, if this script doesn't is executed, jpa will create the tables automatically anyway. The name of the database you need to create is shopping cart.

# Español
El proyecto simula un carrito de compras, donde se pueden agregar y quitar productos del mismo, también pueden crearse y eliminar usuarios, estos se asignan a los carritos y con un endpoint se puede simular el pago del carrito. La función verifica que el usuario tenga saldo suficiente a pagar el total de la sumatoria de los precios de los productos del carrito, caso contrario, se informa mediante una excepción. Todos los endpoints se encuentran documentados con la herramienta swagger, para acceder a la herramienta debe dirigirse a http://localhost:8080/swagger-ui/index.html.

Dentro de source packages hay una carpeta llamada FactorITtest donde puede encontrar una colección de postman con todos los endpoints que contiene la API, además hay 2 archivos SQL donde puede insertar productos o directamente crear las tablas con algunos datos que yo agregué, si este script no se ejecuta, jpa creará las tablas automáticamente de todas maneras. El nombre de la base de datos que debe crear es shoppingcart.
