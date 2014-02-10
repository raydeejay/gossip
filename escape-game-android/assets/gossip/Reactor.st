EVAL Class addNewClass: (Object subclass: 'Reactor' variables: 'javaObject' classVariables: '')
META Reactor
forJavaObject: aJavaObject
	| newReactor |
	newReactor := self new.
	newReactor javaObject: aJavaObject.
	^ newReactor
!
METHOD Reactor
javaObject: aJavaObject
	javaObject := aJavaObject
!
METHOD Reactor
javaObject
	^ javaObject
!
METHOD Reactor
openDoor
	self javaObject invoke: 'switchToState' with: 'open' asJavaObject
!
METHOD Reactor
closeDoor
	self javaObject invoke: 'switchToState' with: 'close' asJavaObject
!
METHOD Reactor
call: aMethod
	self javaObject invoke: aMethod
!
METHOD Reactor
call: aMethod with: first
	self javaObject invoke: aMethod with: first asJavaObject
!
METHOD Reactor
call: aMethod with: first with: second
	self javaObject
	    invoke: aMethod
	    with: first asJavaObject
	    with: second asJavaObject
!
METHOD Reactor
call: aMethod with: first with: second with: third
	self javaObject
	    invoke: aMethod
	    with: first asJavaObject
	    with: second asJavaObject
	    with: third asJavaObject
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
