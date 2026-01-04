✅ **Controllers**
- ✅ Controllers handle only HTTP-related concerns.
- ✅ Validation logic is moved to the service layer or request DTOs with validation annotations.
- ✅ Business logic (e.g., data transformations, string parsing) is in the service layer or mappers, not in controllers.
- ✅ Raw entities are not used for requests or responses; DTOs are used instead.
- ✅ Responses are wrapped in `ResponseEntity<T>` with appropriate HTTP status codes.
- ✅ Specific response types are used (e.g., `ResponseEntity<Map<String, Long>>`, `ResponseEntity<SongDto>`) to maintain API consistency.
- ✅ Controllers do not manually throw or handle exceptions; exceptions are thrown in the service layer and handled globally.
- ✅ Endpoints strictly follow the specified paths without additional prefixes (e.g., `/api/v1/` is not added, etc.).

✅ **Error handling & validation**
- ✅ Implemented global exception handling with `@RestControllerAdvice`.
- ✅ Used the specified error response format for general and validation errors.
- ✅ Enforced all validation rules for song metadata (e.g., correct year format, duration format, required fields).

✅ **Database & Docker**
- ✅ Used PostgreSQL 16+ as the database.
- ✅ Ensured each service has its own dedicated database instance.
- ✅ Database schema initialization is fully automated.
- ✅ Used Hibernate’s `ddl-auto=update` for schema management in this module.
- ✅ Did not use migration tools such as Flyway or Liquibase.
- ✅ Did not use SQL initialization scripts (e.g., `schema.sql`, `data.sql`) in this module.
- ✅ Deployed databases in Docker containers using the [provided Docker Compose file](./docker-compose-file/compose.yaml) (`compose.yaml`).
- ✅ The Docker Compose file is located in the root directory and correctly starts both databases.
- ✅ No Dockerfiles are present, as services must run locally (not in Docker).

✅ **Project structure**
- ✅ Used the correct folder structure.
- [ ] Merged both services into a single Git repository.
- [ ] Created a public Git repository for your project.
- [ ] Excluded IDE-specific configuration files and folders (e.g., `.idea/`, `.vscode/`, `.settings/`, `*.iml`).
- [ ] Ready to place the link to your repository in the personal folder in Avalia.

✅ **API testing**
- ✅ Ran Postman tests using the provided [collection](./api-tests/introduction_to_microservices.postman_collection.json) and [sample MP3 file](./sample-mp3-file/mp3.zip).
- ✅ Verified that all API tests pass.
- ✅ Checked that all API responses conform to the [API response specification](./api-tests/api-response-specification.md).
- [ ] Took screenshots of test results and compiled them into a single PDF or DOCX file.
- [ ] Ready to place the test result PDF or DOCX file in the personal folder in Avalia.
