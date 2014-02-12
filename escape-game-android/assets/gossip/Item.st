EVAL Class addNewClass: (Reactor subclass: 'Item' variables: 'key destination imageForOpen imageForClosed imageForLocked' classVariables: '')
METHOD Item
initialize
    | default |
    super initialize.
    default := State new.
    default name: #default.
    default onClick: [ self ]. 
    
    self addState: default.
    self switchToState: #default.
!
