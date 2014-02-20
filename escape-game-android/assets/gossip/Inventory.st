EVAL Class addNewClass: (JavaWrapper subclass: 'Inventory' variables: 'selected' classVariables: '')
METHOD Inventory
initialize
    super initialize.
!
METHOD Inventory
background: aString
    self call: #setBackgroundFilename with: aString.
!

METHOD Inventory
selected
    ^ selected.
!
METHOD Inventory
selected: anItem
    selected := anItem.
    anItem beSelected.
!
METHOD Inventory
clearSelected
	selected beUnselected.
    selected := nil.
!

METHOD Inventory
add: anItem
    javaObject invoke: #addItem with: anItem javaObject.
!
METHOD Inventory
clear
    javaObject invoke: #clearInventory.
!
META Inventory
getAndClear
	| inv |
	inv := GameRegistry instance getInventory.
	inv clear.
!
METHOD Inventory
remove: aJavaObject
    javaObject invoke: #removeItem with: aJavaObject.
!
