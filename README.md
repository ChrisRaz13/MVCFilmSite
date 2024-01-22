# MVCFilmSite
# SpringMVCFilmCRUD

## Description
This project implements full web-based C.R.U.D. (Create, Read, Update, Delete) functionality for films using Spring MVC and the DAO pattern. The DAO implementation utilizes JDBC to persist and retrieve data.

## Instructions

### How to Use
1. **View Film Details:**
   - Enter a Film's ID to view its details on the web page.
  

2. **Add a New Film:**
   - Choose to add a new film.
   - Enter all the properties of the film.
   - Submit the form to create a new Film object, which will be saved in the database.

3. **Delete a Film:**
   - Retrieve a film.
   - Choose to delete it.

4. **Edit a Film:**
   (NOTE: Sadly, we werent able to figure this out. However, It was supposed to work as below)
   - Retrieve a film.
   - Choose to edit it.
   - All current properties will be displayed in a form, allowing changes (except ID).
   - Submit the form to update the film's record in the database.
   - If the update fails, you will be informed.

5. **Search for Films:**
   - Search for films by keyword/pattern in title or description.
   - From the resulting list, choose to update or delete a record.

6. **Display Film Details:**
   - When a film's details are displayed, its actors are also listed.

### Tech Used
- Spring MVC
- DAO Pattern
- JDBC
- Gradle

## Simple Lessons Learned

- Application of the DAO pattern for data access.
- Utilization of JDBC for persisting and retrieving data.

