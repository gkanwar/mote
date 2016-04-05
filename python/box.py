import config
import draw
from symbol import Symbol
from util import Pos

class Box:
    children = []
    childPos = []
    symbols = []
    symbolPos = []
    width = None
    height = None

    def __init__(self, children, childPos, symbols, symbolPos, width, height):
        # DEBUG assertions
        for c in children:
            assert isinstance(c, Box)
        for cpos in childPos:
            assert isinstance(cpos, Pos)
        for s in symbols:
            assert isinstance(s, Symbol)
        for spos in symbolPos:
            assert isinstance(spos, Pos)
        assert isinstance(width, int)
        assert isinstance(height, int)
            
        self.children = children
        self.childPos = childPos
        self.symbols = symbols
        self.symbolPos = symbolPos
        self.width = width
        self.height = height

    def draw(self, srf, pos=Pos(0,0)):
        if config.DEBUG:
            # TODO: Draw bounding box
            pass
        for (child, cpos) in zip(self.children, self.childPos):
            child.draw(srf, cpos + pos)
        for (symbol, spos) in zip(self.symbols, self.symbolPos):
            symbol.draw(srf, spos + pos)

def buildHorizBox(items):
    off = Pos(0,0)
    children = []
    childPos = []
    symbols = []
    symbolPos = []
    height = 0
    for i in items:
        # DEBUG assertions
        assert isinstance(i, Box) or isinstance(i, Symbol), i
        if isinstance(i, Box):
            children.append(i)
            childPos.append(off)
            off += Pos(i.width,0)
            height = max(height, i.height)
        elif isinstance(i, Symbol):
            symbols.append(i)
            symbolPos.append(off)
            size = i.getSize()
            off += Pos(size[0],0)
            height = max(height, size[1])
    return Box(children, childPos, symbols, symbolPos, off.x, height)
