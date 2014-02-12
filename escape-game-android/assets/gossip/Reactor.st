EVAL Class addNewClass: (JavaWrapper subclass: 'Reactor' variables: 'states currentState' classVariables: '')
METHOD Reactor
javaObject: aJavaObject
	javaObject := aJavaObject.
    <150 javaObject [ 
        "System println: 'clicked from ST'."
        currentState clicked. ]>.
!
METHOD Reactor
initialize
    super initialize.
"    javaObject := (System instantiateJavaClass: 'net.raydeejay.escapegame.Reactor')."
    states := Dictionary new.
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
METHOD Reactor
addState: aState
    states at: aState name put: aState.
!
METHOD Reactor
switchToState: aStateName
    currentState notNil ifTrue: [ currentState exit. ].
    currentState := (states at: aStateName).
    currentState enter.
!