EVAL Class addNewClass: (JavaWrapper subclass: 'Reactor' variables: '' classVariables: '')
METHOD Reactor
openDoor
	self javaObject invoke: 'switchToState' with: 'open' asJavaObject
!
METHOD Reactor
closeDoor
	self javaObject invoke: 'switchToState' with: 'close' asJavaObject
!
METHOD Reactor
onClick: aBlock
	<150 javaObject aBlock>
!
METHOD Reactor
setImage: aString
    self call: #setImage with: aString.
!
METHOD Reactor
addToRoomNamed: aString
    self call: #addToRoomNamed with: aString
!
METHOD Reactor
setX: x Y: y
    self call: #at with: x with: y.
!
METHOD Reactor
moveToInventory
	self call: #moveToInventory
!
