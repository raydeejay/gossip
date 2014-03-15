EVAL Class addNewClass: (Markup subclass: 'HTMLMarkup' variables: '' classVariables: '')

COMMENT HTMLMarkup
I am an HTML generator.
!

METHOD HTMLMarkup
initialize
    super initialize.
    self begin
!

METHOD HTMLMarkup
render
    self end.
    ^ contents
!

METHOD HTMLMarkup
extension
    ^ '.html'
!

METHOD HTMLMarkup
begin
    contents := contents , '<html><body>'
!

METHOD HTMLMarkup
end
    contents := contents , '<body></html>'
!

METHOD HTMLMarkup
newline
    contents := contents , '<br>' , Char newline asString
!

METHOD HTMLMarkup
tab
    contents := contents , '&#09;'
!

METHOD HTMLMarkup
heading: aString level: aNumber
    | level |

    level := aNumber asString.
    contents := contents , '<h' , level, '>' , aString , '</h' , level , '>'
!

METHOD HTMLMarkup
link: aString to: aURL
    contents := contents , '<a href="' , aURL , '">' , aString , '</a>'
!

METHOD HTMLMarkup
code: aString
    contents := contents , '<pre>' , aString , '</pre>'
!

METHOD HTMLMarkup
blockQuote: aString
    contents := contents , '<blockquote>' , aString , '</blockquote>'
!

METHOD HTMLMarkup
italic: aString
    contents := contents , '<i>' , aString , '</i>'
!

METHOD HTMLMarkup
bold: aString
    contents := contents , '<b>' , aString , '</b>'
!

METHOD HTMLMarkup
underline: aString
    contents := contents , '<u>' , aString , '</u>'
!

METHOD HTMLMarkup
bar
    contents := contents , '<hr/>'
!

METHOD HTMLMarkup
image: aURL
    contents := contents , '<img src="' , aURL , '" />'
!

METHOD HTMLMarkup
list: aBlock
    contents := contents , '<ul>'.
    aBlock value.
    contents := contents , '</ul>'
!

METHOD HTMLMarkup
orderedList: aBlock
    self orderedList: aBlock start: 1
!

METHOD HTMLMarkup
orderedList: aBlock start: aNumber
    contents := contents , '<ol start="', aNumber printString , '">'.
    aBlock value.
    contents := contents , '</ol>'
!

METHOD HTMLMarkup
item: aString
    contents := contents , '<li>' , aString , '</li>'
!

