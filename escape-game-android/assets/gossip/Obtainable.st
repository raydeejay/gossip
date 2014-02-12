EVAL Class addNewClass: (Reactor subclass: 'Obtainable' variables: '' classVariables: '')
METHOD Obtainable
initialize
    | default |
    super initialize.
    default := State new.
    default name: #default.
    default onClick: [ self moveToInventory ]. 
    
    self addState: default.
    self switchToState: #default.
!
