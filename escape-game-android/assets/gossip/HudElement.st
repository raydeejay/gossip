EVAL Class addNewClass: (Reactor subclass: 'HudElement' variables: '' classVariables: '')
METHOD HudElement
initialize
    | default |
    super initialize.
    default := State new.
    default name: #default.
    default onClick: [ self ]. 
    
    self addState: default.
    self switchToState: #default.
!
METHOD HudElement
onClick: aBlock
    (states at: #default) onClick: aBlock 
!
