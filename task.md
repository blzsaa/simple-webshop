###Application Assignment
####Web service
####Task
Create a tiny REST / JSON web service in Java using Spring Boot (RestController) with an API that
supports basic products CRUD:
1. Create a new product
1. Get a list of all products
1. Update a product
1. Placing an order
1. Retrieving all orders within a given time period

A product should have a name and some representation of its price.

Each order should be recorded and have a list of products. It should also have the buyer’s e-mail, and the time the order was placed. The total value of the order should always be calculated, based on the prices of the products in it.

It should be possible to change the product’s price, but this shouldn’t affect the total value of orders which have already been placed.

#### Requirements
1. Implement your solution according to the above specification.
1. Provide unit tests.
1. Document your REST-API.
1. Provide a storage solution for persisting the web service’s state.
1. Have a way to run the service with its dependencies (database etc) locally. You can use either a simple script or docker or something else. It’s up to you.

#### Submitting
1. Upload your code to a public git repository (eg. GitHub, GitLab) and send us the link.
#### Considerations
1. You do not need to add authentication to your web service, but propose a protocol / method and
justify your choice.*
1. How can you make the service redundant? What considerations should you do?*

*Please provide your answers in the README file.