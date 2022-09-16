# Market Cart
## API Rest creada con Java ♨ y Spring Boot 🍃
El proyecto simula un carrito de compras, donde se pueden agregar y quitar productos del mismo, también pueden crearse y eliminar usuarios, estos se asignan a los carritos y con un endpoint se puede simular el pago del carrito. La función verifica que el usuario tenga saldo suficiente a pagar el total de la sumatoria de los precios de los productos del carrito, caso contrario, se informa mediante una excepción. Todos los endpoints se encuentran documentados con la herramienta swagger, para acceder a la herramienta debe dirigirse a http://localhost:8080/swagger-ui/index.html.

Dentro de source packages hay una carpeta llamada FactorITtest donde puede econtrar una colección de postman con todos los endpoints que contiene la API, además hay 2 archivos SQL donde puede insertar productos o directamente crear las tablas con algunos datos que yo agregué, si este script no se ejecuta, jpa creará las tablas automáticamente de todas maneras. El nombre de la base de datos que debe crear  es shoppingcart
