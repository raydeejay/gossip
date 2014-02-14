// This little hack allows us to refer to GameRegistry class even when its
// methods can't be defined yet, because they reference all the other classes
//EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: 'reactors rooms' classVariables: 'instance javaObject')

META GameRegistry
instance
    instance isNil ifTrue: [ instance := self new ].
    ^ instance
!
METHOD GameRegistry
initialize
    super initialize.
    reactors := Dictionary new.
    rooms := Dictionary new.
!

META GameRegistry
javaObject
	javaObject isNil ifTrue: [ javaObject := (System getJavaClass: 'net.raydeejay.escapegame.GameRegistry') invokeStatic: 'instance' ].
	^ javaObject. 
!
META GameRegistry
newReactor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Reactor forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newRoom: aString
	| aRoom |
	aRoom := (self javaObject invoke: 'newRoom' with: aString asJavaObject).
	^ ((Room forJavaObject: aRoom) name: aString) register.
!
META GameRegistry
newDoor: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Door forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newObtainable: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Obtainable forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
newDropper: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Dropper forJavaObject: aReactor) name: aString) register.
!
META GameRegistry
getSelectedItem
	| anItem |
	anItem := self javaObject invoke: #getSelectedItem.
	anItem printString = 'JavaObject<null>'
	    ifTrue: [ ^ nil ]
	    ifFalse: [ ^ (Item new javaObjectKeepListener: anItem) name: (anItem invoke: #getName) asSmallString ].
!
META GameRegistry
clearSelectedItem
    self javaObject invoke: #clearSelectedItem.
!
META GameRegistry
removeItem: aJavaObject
    self javaObject invoke: #removeItem with: aJavaObject.
!
META GameRegistry
switchToRoom: aString
    self javaObject invoke: #switchToRoom with: aString asJavaObject.
!

METHOD GameRegistry
registerReactor: aReactor
    reactors at: aReactor name put: aReactor.
!
METHOD GameRegistry
registerRoom: aRoom
    rooms at: aRoom name put: aRoom.
!
METHOD GameRegistry
getReactor: aString
	^ reactors at: aString ifAbsent: [ nil ].
!
METHOD GameRegistry
getRoom: aString
	^ rooms at: aString ifAbsent: [ nil ].
!

METHOD GameRegistry
switchToRoom: aString
    self class javaObject invoke: #switchToRoom with: aString asJavaObject.
!
METHOD GameRegistry
winGame
    self class javaObject invoke: #winGame.
!
