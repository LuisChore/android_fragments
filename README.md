# [Note Taking App](https://github.com/LuisChore/android_fragments/tree/main/04NoteApp)

## Description
App that allows the user to manage notes, allowing add, search by title and text, delete and update them through a local database (Room), App developed using the MVVM pattern. The notes will be displayed in a staggered format (Staggered Layout Manager)

## Android Topics
* **Parcelable**: Interface that a class can implement to be passed within and intent from an Activity to another one. Method for transporting data from one activity to another one.
* **suspend functions**: Used in coroutines.  Suspend function is a simple function which can be paused and resumed later in your code.
* **@Volatile**: Annotation used in order to force changes in a variable to be immediately visible to other threads




### MVVM Architecture pattern steps

* Model
    * Room Database (local data source) 
        * Entity 
        * Data Access Object 
        * Database 
    * Repository 
* ViewModel
* View

### Room steps
*build.gradle.kts*

1. Add dependencies  and plugins
    a. Room dependency 
    b. Kotlin annotation processing tool (kapt) dependency and plugin 


Add entity data classes   *Note.kt*
1. Add annotations @Entity, @PrimaryKey, @ColumnInfo  
2. Add Parcelize plugins
3. Implements Parcelable


Add Data Access Object interfaces *NoteDAO.kt*
1. Add annotation @Dao
2. Add CRUD operations @Insert, @Delete, @Update and ‘suspend’ functions
3. Add queries using @Query and returning LiveData wrappers. 

Add Database abstract class extending RoomDatabase  *NoteDatabase.kt*

1. Add Database abstract class extending RoomDatabase
2. Add annotation @Database(entities,version ) 
3. Add abstract getters for the DAOs
4. Add a static synchronized getter function for the database instance. 

### Repository Steps

Create Repository class  (*NoteRepository.kt*)
Room Database Source:

1. The constructor receives a DatabaseRoom instance
2. Each method in the DAOs interfaces should be mentioned as a method in the repository using suspend functions where necessary to run in the background thread.

*MainActivity.kt*

1. Create a repository instance	for the ViewModel


### ViewModel Steps

Create a class extending AndroidViewModel (*NoteViewModel.kt*)

1. The constructor receives a Repository instance and Application instance   
2. Add the necessary methods provided by the Repository. 
3. Use coroutines where necessary to run in the background thread.

Create a class extending ViewModelProvider.Factory (*NoteViewModelFactory.kt*)

1. Link the ViewModel and ViewModelFactory with create override method.

*MainActivity.kt*

1. Create a NoteViewModelFactory instance
    ```
    viewModelProviderFactory = NoteViewModelFactory(application,repository)
    ```
2. Get a viewModel instance using ViewModelProvider

*HomeFragment.kt*

1. Get a ViewModel instance from MainActivity 
    ```
    viewModel = (activity as MainActivity).viewModel
    ```
2. Add observers to the LiveData objects using the ViewModel instance 	

*NewNoteFragment.kt*

1. Get a ViewModel instance from MainActivity 
    ```
    viewModel = (activity as MainActivity).viewModel
    ```
2. Call CRUD functions using the ViewModel instance






### Navigation Component Steps
*build.gradle.kts*

1. Add dependencies and plugins
    a. Add Navigation Dependencies (module)
    b. Add Safe Args plugin (project)

*res/navigation/nav_graph.xml*

1. Create NavigationGraph Resource
 
*activity_main.xml*

1. Add a NavHostFragment layout  (FragmentContainerView)

*res/navigation/nav_graph.xml*

1. Add new Destinations in the NavigationGraph 
     (*HomeFragment,NewNoteFragment,UpdateNoteFragment*)
2. Add action between destinations
     Add an <action> in the NavigationGraph
4. Add argument to destinations
     Add an <argument> to send data (using Directions) 


*HomeFragment.kt*

1. Navigate to another destination using Action
    a. Navigate using findNavController().navigate

*NoteAdapter.kt* (send args)

1. Send data and navigate to another destination using Directions
    a. Create a direction
    b. Navigate using findNavController().navigate


*UpdateNoteFragment.kt*  (recover args)

1. Create a FragmentArgs instance
    ```
     val args: UpdateNoteFragmentArgs by navArgs()
     ```
2. Recover args
    ```
    val obj = args.nameArg!!
    ```


### Data Binding Steps

*build.gradle.kts*

1. Enable DataBinding buildFeatures

*fragment_home.xml*

1. Wrap xml file using <layout>

*HomeFragment.kt*

1. Pass the layout to the Fragment() constructor
2. Create a LayoutBinding instance (binding)
3. Inflate layout using binding.inflate()  //onCreateView()
4. Set to null the binding instance //onDestroy() 



### RecyclerView steps

1. Create an AdapterView (RecyclerView layout) *home_fragment.xml*
2. Create a custom layout for the items (*note_item.xml*)
3. Create  a custom Model Class to represent each of the items, a template for  the data we will pass (*Note.kt*)
4. Modify the layout xml file to have a bind reference (*note_item.xml*)
    ```
    <layout></layout>
    ```
5. Create a Custom Adapter Class (*NoteAdapter.kt*)
    a.  extends RecyclerView.Adapter<CustomAdapter.ViewHolder> 
    b. ViewHolder Class using DataBinding
    c. Use diffUtil to manage currentList
    d. Override Methods using DataBinding
    - onCreateViewHolder
    - onBindViewHolder
        - Add setOnClickListener() 
    - getItemCount()

*HomeFragment.kt*

1. Create an Adapter instance 
2. Setup the RecyclerView using binding.recyclerView
    a. layoutManager (StaggeredGridLayoutManager)
    b. setHasFixedSize(true) 
    c. adapter
3. Add observers to the LiveData objects using ViewModel instance 	
4. Use adapter.differ to update List


### DiffUtil steps

*NoteAdapter.kt*

1. Create a DiffUtil.ItemCallback object and implement the override methods (differCallback)
    ```
    areItemsTheSame()
    areContentsTheSame()
    ```
2. Create an AsyncListDiffer object 
    ```
    differ = AsyncListDiffer(this,differCallback)
    ```
3. Reference the current list using the AsyncListDiffer
    ```
    differ.currentList
    ```
*HomeFragment.kt*

1. Update the list in the ViewModel observers
    ```
    adapter.diifer.sumbitList(val)
    ```
