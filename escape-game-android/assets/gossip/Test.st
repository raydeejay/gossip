////////////////////////////////////////////////////////////
// Syntax tests
//
// Author: Sergi Reyner
//
// (also figure out how to alter/tweak/replace the syntax)
////////////////////////////////////////////////////////////
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
EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: '' classVariables: 'javaObject')
META GameRegistry
javaObject
	javaObject := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance'.
	^ javaObject 
!
META GameRegistry
getReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'getReactor' with: aString asJavaObject).
	^ Reactor forJavaObject: aReactor
!
EVAL Transcript open
