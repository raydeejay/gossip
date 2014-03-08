EVAL Class addNewClass: (Reactor subclass: 'Obtainable' variables: '' classVariables: '')
METHOD Obtainable
initialize
    | default |
    super initialize.
    default := State new.
    default name: #default.
    default onClick: [ self moveToInventory. ]. 
    
    self addState: default.
    self switchToState: #default.
!

META GameRegistry
newObtainable: aString
	| aReactor |
	aReactor := (self javaObject invoke: 'newReactor' with: aString asJavaObject).
	^ ((Obtainable forJavaObject: aReactor) name: aString) register.
!
