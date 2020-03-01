# Model View View Model Architecture

User can perform CRUD operations on a business. User gives business note a priority which indicates position in list.This data 
survives configuration changes. A searchView is used to search for a specific business by name.

## How it Works

This app uses Model View View Model Architecture for loose coupling. User perform CRUD operations on a business note CRUD 
operations on a business name, description and a priority. This data is created and updated on the add Business activity. This 
data is then sent to the Main Activity using startActivityForResult where the data is sent to a View Model. This View Model then calls a triggers a repository
method which then triggers a DAO method. This DAO interface then creates and updates the Business Database, which has one 
Business table inside it. This same structure is used to Delete and Read data from the database. These database operations use
LiveData. As soon as a LiveData object changes the subsequent classes are triggered and the database is updated. Then the 
RecyclerView Adapter is triggered and sent the LiveData Object to compare the old RecyclerView to the new RecyclerView. If there 
is a difference between the RecyclerView before the CRUD operation and after the CRUD operation the RecyclerView will reflect
that change. A searchView uses a QueryTextListener that is triggered everytime the text is changed. This searchView compares
all the Business notes to the input text from the user. If there is a match the business note or business notes are then displayed.

