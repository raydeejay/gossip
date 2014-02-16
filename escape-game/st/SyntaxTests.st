////////////////////////////////////////////////////////////
// Syntax tests
//
// Author: Sergi Reyner
//
// NOTE: figure out how to write class methods
// (also figure out how to alter/tweak/replace the syntax)
////////////////////////////////////////////////////////////
EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: '' classVariables: 'javaObject')
META GameRegistry
javaObject
	^ javaObject isNil ifTrue: [ 
		javaObject := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') ]  
!
META GameRegistry
getReactor: aString
	^ Reactor forJavaObject: (self javaObject invoke: 'getReactor' with: aString)
!
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
javaObject: aJavaObject
	^ javaObject
!
