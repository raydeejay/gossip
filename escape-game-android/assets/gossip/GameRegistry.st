EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: '' classVariables: 'javaObject')
META GameRegistry
javaObject
	javaObject := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance'.
	^ javaObject 
!
META GameRegistry
newReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ Reactor forJavaObject: aReactor
!
META GameRegistry
getReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'getReactor' with: aString asJavaObject).
	^ Reactor forJavaObject: aReactor
!
META GameRegistry
newRoom: aString
	| aRoom |
	aRoom := (self javaObject invoke: 'newRoom' with: aString asJavaObject).
	^ Room forJavaObject: aRoom
!
META GameRegistry
getRoom: aString
	| aRoom |
	aRoom := (self javaObject invoke: 'getRoom' with: aString asJavaObject).
	^ Room forJavaObject: aRoom
!
META GameRegistry
newDoor: aString
	| aDoor |
	aDoor := (self javaObject invoke: 'newDoor' with: aString asJavaObject).
	^ Door forJavaObject: aDoor
!
