Objective:
    Create a simple Android application that allows users to manage a list of tickets. The app should include functionality for adding, viewing, editing and deleting tickets.
Requirements:
1. Main Features:
  ○ Ticket List: Display a list of tickets with the following details:
    ■ Ticket name
    ■ Ticket description
    ■ Ticket priority
    ■ Due date (optional)
  ○ Add Ticket: Allow users to add a new Ticket with a name, description, priority and due date.
  ○ View & Edit Ticket Details: Users should be able to tap on a Ticket to view & edit its details on a separate screen.
  ○ Delete Ticket: Allow users to delete a Ticket from the list.
2. User Interface:
  ○ Main Screen:
    ■ A RecyclerView or ListView to display the list of Tickets .
    ■ There should be a button to add a new ticket.
  ○ Add/Edit Screen:
    ■ Input fields for name and description.
    ■ Priority (use a dropdown view for priority(low,high and medium).
    ■ Due date ( use a date picker for the due date).
    ■ Buttons to save or cancel.
  ○ Detail Screen:
    ■ Display ticket name, description, priority, and due date.
    ■ An option to delete and edit the ticket.
3. Data Storage:
  ○ Use SQLite or Room Database to store data.
  ○ Ensure that data persists across app restarts.
4. Validation:
  ○ Ensure that the name description and priority fields are not empty.
5. Additional Features (Optional but encouraged):
  ○ Implement basic data filtering or sorting (e.g., by priority).
  ○ Add basic animations or transitions for a better user experience.
Technical Requirements:
  ● Use Kotlin for development.
  ● Adhere to modern Android development practices (e.g., MVVM architecture, LiveData, ViewModel).
  ● Utilize Material Design principles for UI components.
  ● Ensure the app is responsive and works on different screen sizes.
