EVAL Class addNewClass: (Reactor subclass: 'Item' variables: '' classVariables: '')
METHOD Item
initialize
    | default |
    super initialize.
    default := State new.
    default name: #default.
    default onClick: [ self clicked ]. 
    
    self addState: default.
    self switchToState: #default.
!
METHOD Item
addToInventory
    self.
!
METHOD Item
removeFromInventory
    self.
!
METHOD Item
beUnselected
    self call: #beUnselected.
    GameRegistry instance clearSelectedItem.
!
METHOD Item
beSelected
    self call: #beSelected.
    GameRegistry instance setSelectedItem: self.
!
METHOD Item
isSelected
    | s | 
    s := GameRegistry instance getSelectedItem.
    s isNil
        ifTrue: [ ^ false ]
        ifFalse: [ ^ s name = name ].
!
METHOD Item
clicked
    | s | 
    self isSelected
        ifTrue: [ self beUnselected ]
        ifFalse: [ 
            s := GameRegistry instance getSelectedItem.
            s isNil
                ifTrue: [ self beSelected ]
                ifFalse: [ self "combine items" ] ].
!
