EVAL Class addNewClass: ( Menu subclass: 'PopupMenu' variables: '' classVariables: '')

META PopupMenu
new
    self error: 'A popup menu must be created with #new:'
!

META PopupMenu
new: t
   ^ <92 self t>
!

METHOD PopupMenu
on: title do: action
   <93 self title action>
!

METHOD PopupMenu
attachTo: aComponent
    <94 aComponent self>
!
