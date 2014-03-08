// tests with moving all the game to the Gossip side

EVAL Class addNewClass: (Object subclass: 'SetupGossip' variables: '' classVariables: '')
META SetupGossip
setup
    self setupNavigation.
    (GameRegistry newRoom: #title) background: 'gossip/gossip.png'.
!
META SetupGossip
start
    GameRegistry instance switchToRoom: #title
!

// This should not be required here

META SetupGossip
setupNavigation
    | left right |
    
    left := (GameRegistry newHudElement: #arrowLeft) onClick: [ 
        GameRegistry instance switchToRoom: GameRegistry instance currentRoom left ].
    left setImage: 'demo/sprites/arrowLeft.png'.
    left setX: 10 Y: 240.
    right := (GameRegistry newHudElement: #arrowRight) onClick: [ 
        GameRegistry instance switchToRoom: GameRegistry instance currentRoom right ].
    right setImage: 'demo/sprites/arrowRight.png'.
    right setX: 620 Y: 240.
!
