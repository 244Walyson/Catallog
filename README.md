# Projeto: walyCatallog - Catálogo de Produtos

O projeto **walyCatallog** é um catálogo de produtos que tem como principal objetivo listar produtos. Ele apresenta uma série de recursos avançados, incluindo controle de acesso e liberação de recursos com base no perfil do usuário. A autenticação é realizada através de tokens JWT, proporcionando um ambiente seguro. Além disso, o projeto é acompanhado por testes automatizados e testes de API utilizando a biblioteca Rest Assured.

## Principais Características:

- **Controle de Acesso e Permissões:** O projeto implementa um sistema de controle de acesso que define as permissões dos usuários com base em seus perfis. Isso garante que apenas usuários autorizados tenham acesso a determinados recursos.

- **Autenticação Segura:** A autenticação é realizada por meio de tokens JWT (JSON Web Tokens), proporcionando uma forma segura de autenticação e autorização para os usuários.

- **Testes Automatizados e Testes de API:** O projeto inclui testes automatizados para garantir a qualidade do código e a funcionalidade correta. Além disso, os testes de API são realizados usando a biblioteca Rest Assured, assegurando que os endpoints da API estejam funcionando conforme o esperado.

- **Recuperação de Senha por E-mail:** Um sistema de recuperação de senha foi implementado, permitindo que os usuários redefinam suas senhas através do envio de um e-mail com um token personalizado para o endereço cadastrado.

- **Documentação com Swagger:** Os endpoints da API estão documentados usando a plataforma Swagger, tornando mais fácil para os desenvolvedores entenderem e utilizarem a API.

- **HATEOAS para uma API Restful:** O projeto utiliza HATEOAS (Hypertext as the Engine of Application State) para melhorar a experiência da API, fornecendo links relacionados e navegáveis para recursos adicionais.

## Tecnologias Utilizadas:

O projeto foi desenvolvido utilizando a tecnologia Java Spring, fazendo uso de diversas tecnologias do ecossistema Spring, incluindo Spring Data JPA, Spring Security e Spring MVC. Além disso, a solução AWS S3 foi empregada para o armazenamento das imagens dos produtos.

## Modelo conceitual do projeto:

![image](https://github.com/244Walyson/walyCatallog/assets/125759796/c136ae82-bb29-44c9-b47a-de138f9df883)

# endpoints da apicação:

# Login
**Endpoint:** `/oauth2/token`
**Method:** POST

# User Controller

## Get All Users

- **Endpoint:** `/users`
- **Method:** GET
- **Authorization:** Requires ROLE_ADMIN role
- **Query Parameters:** 
  - pageable: Page number and size
- **Response:** `Page<UserDTO>`

## Get User by ID

- **Endpoint:** `/users/{id}`
- **Method:** GET
- **Authorization:** Requires ROLE_ADMIN role
- **Path Parameters:**
  - id: User ID
- **Response:** `UserDTO`

## Insert New User

- **Endpoint:** `/users`
- **Method:** POST
- **Request Body:** `UserInsertDTO`
- **Response:** `UserDTO`

## Update User

- **Endpoint:** `/users/{id}`
- **Method:** PUT
- **Authorization:** Requires ROLE_ADMIN role
- **Path Parameters:**
  - id: User ID
- **Request Body:** `UserUpdateDTO`
- **Response:** `UserDTO`

## Delete User

- **Endpoint:** `/users/{id}`
- **Method:** DELETE
- **Authorization:** Requires ROLE_ADMIN role
- **Path Parameters:**
  - id: User ID
- **Response:** No content

## Get Current User Profile

- **Endpoint:** `/users/profile`
- **Method:** GET
- **Response:** `UserDTO`

# User Auth Controller

## Create Recover Token

- **Endpoint:** `/auth/recover-token`
- **Method:** POST
- **Request Body:** `EmailDTO`
- **Response:** No content

## Save New Password

- **Endpoint:** `/auth/new-password`
- **Method:** PUT
- **Request Body:** `NewPasswordDTO`
- **Response:** No content

# Product Controller

## Get All Products

- **Endpoint:** `/products`
- **Method:** GET
- **Query Parameters:**
  - pageable: Page number and size
  - name: Product name (optional)
  - categoryid: Category IDs (optional, comma-separated)
- **Response:** `Page<ProductDTO>`

## Get Product by ID

- **Endpoint:** `/products/{id}`
- **Method:** GET
- **Path Parameters:**
  - id: Product ID
- **Response:** `ProductDTO`

## Insert New Product

- **Endpoint:** `/products`
- **Method:** POST
- **Authorization:** Requires ROLE_ADMIN role
- **Request Body:** `ProductDTO`
- **Response:** `ProductDTO`

## Update Product

- **Endpoint:** `/products/{id}`
- **Method:** PUT
- **Authorization:** Requires ROLE_ADMIN role
- **Path Parameters:**
  - id: Product ID
- **Request Body:** `ProductDTO`
- **Response:** `ProductDTO`

## Delete Product

- **Endpoint:** `/products/{id}`
- **Method:** DELETE
- **Authorization:** Requires ROLE_ADMIN role
- **Path Parameters:**
  - id: Product ID
- **Response:** No content

## Upload Product Image

- **Endpoint:** `/products/image`
- **Method:** POST
- **Authorization:** Requires ROLE_ADMIN role
- **Request Body:** Image file (multipart/form-data)
- **Response:** `UriDTO`

# Category Controller

## Get All Categories

- **Endpoint:** `/categories`
- **Method:** GET
- **Response:** List of `CategoryDTO`

## Get Category by ID

- **Endpoint:** `/categories/{id}`
- **Method:** GET
- **Path Parameters:**
  - id: Category ID
- **Response:** `CategoryDTO`

## Insert New Category

- **Endpoint:** `/categories`
- **Method:** POST
- **Authorization:** Requires ROLE_ADMIN role
- **Request Body:** `CategoryDTO`
- **Response:** `CategoryDTO`

## Update Category

- **Endpoint:** `/categories/{id}`
- **Method:** PUT
- **Authorization:** Requires ROLE_ADMIN role
- **Path Parameters:**
  - id: Category ID
- **Request Body:** `CategoryDTO`
- **Response:** `CategoryDTO`

## Delete Category

- **Endpoint:** `/categories/{id}`
- **Method:** DELETE
- **Authorization:** Requires ROLE_ADMIN role
- **Path Parameters:**
  - id: Category ID
- **Response:** No content
