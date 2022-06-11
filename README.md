# Kemary

Kemary is a warehouse system for private users. Which supports barcodes and custom inputs.
It is designed to manage household items and grocceries. 
Also it will be possible to use Kemary for recipies and auto generate shopping lists.

## Techstack
### frontend
The frontend is the all in one platform to controll everything within Kemary.
#### used technology
- nuxt
- stylus
- lost

### backend
The backend is a Java Spring enviroment. Which is build on Docker. And is divided in different APIs.

#### APIs
- [warehouse](#warehouse)
#### warehouse
Within warehouse are all items like grocceries and household items categories. So this API nessesary to use Kemary.

##### used technology
- Java Spring Boot
- Hibernate
- MySQL (Database)
- lombok