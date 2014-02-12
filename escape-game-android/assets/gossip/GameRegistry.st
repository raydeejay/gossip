EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: '' classVariables: 'javaObject')
META GameRegistry
javaObject
	javaObject := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance'.
	^ javaObject. 
!
META GameRegistry
newReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ Reactor forJavaObject: aReactor.
!
META GameRegistry
getReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'getReactor' with: aString asJavaObject).
	^ Reactor forJavaObject: aReactor.
!
META GameRegistry
newRoom: aString
	| aRoom |
	aRoom := (self javaObject invoke: 'newRoom' with: aString asJavaObject).
	^ Room forJavaObject: aRoom.
!
META GameRegistry
getRoom: aString
	| aRoom |
	aRoom := (self javaObject invoke: 'getRoom' with: aString asJavaObject).
	^ Room forJavaObject: aRoom.
!
META GameRegistry
newDoor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ Door forJavaObject: aReactor.
!
META GameRegistry
newObtainable: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ Obtainable forJavaObject: aReactor.
!
META GameRegistry
getSelectedItem
	| anItem |
	anItem := self javaObject invoke: #getSelectedItem.
	^ Item forJavaObject: anItem.
!
META GameRegistry
clearSelectedItem
    self javaObject invoke: #clearSelectedItem.
!
META GameRegistry
removeItem: aJavaObject
    self javaObject invoke: #clearSelectedItem with: aJavaObject.
!
META GameRegistry
switchToRoom: aString
    self javaObject invoke: #switchToRoom with: aString asJavaObject.
!
