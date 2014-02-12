EVAL Class addNewClass: (Reactor subclass: 'Obtainable' variables: '' classVariables: '')
METHOD Obtainable
initialize
    | default |
    super initialize.
    default := State new.
    default name: #default.
    default onClick: [ 
        System println: 'obtainable onClick invoked once'.
        self moveToInventory ]. 
    
    self addState: default.
    self switchToState: #default.
!
