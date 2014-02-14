// This little hack allows us to refer to GameRegistry class even when its
// methods can't be defined yet, because they reference all the other classes
//EVAL Class addNewClass: (Object subclass: 'GameRegistry' variables: 'reactors rooms inventory javaObject' classVariables: 'instance javaObject')

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
    javaObject := self class javaObject.
    inventory := Inventory forJavaObject: (javaObject invoke: 'getInventory').
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
newItemFrom: aReactor
    | anItem anItemJO aString |

    aString := (aReactor name + 'Item').
    anItemJO := (aReactor javaObject invoke: 'asItem').

"    anItem := Item new javaObjectKeepListener: anItemJO."
    anItem := Item forJavaObject: anItemJO.
    anItem name: aString.
    anItem setImage: aReactor getImage.
    anItem register.
    ^ anItem
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
getInventory
	^ inventory.
!
METHOD GameRegistry
switchToRoom: aString
    self class javaObject invoke: #switchToRoom with: aString asJavaObject.
!

METHOD GameRegistry
getSelectedItem
	| anItem |
	anItem := javaObject invoke: #getSelectedItem.
	anItem printString = 'JavaObject<null>'
	    ifTrue: [ ^ nil ]
	    ifFalse: [ 
	        ^ (Item new javaObjectKeepListener: anItem) name: (anItem invoke: #getName) asSmallString ].
!
METHOD GameRegistry
setSelectedItem: anItem
    javaObject invoke: #setSelectedItem with: anItem javaObject.
!
METHOD GameRegistry
clearSelectedItem
    javaObject invoke: #clearSelectedItem.
!
METHOD GameRegistry
addToInventory: anItem
    javaObject invoke: #addToInventory with: anItem javaObject.
"    inventory add: anItem."
!
METHOD GameRegistry
removeItem: aJavaObject
    javaObject invoke: #removeItem with: aJavaObject.
"    inventory remove: aJavaObject."
!

METHOD GameRegistry
winGame
    self class javaObject invoke: #winGame.
!
