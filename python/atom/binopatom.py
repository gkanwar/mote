import draw
from box import *
from symbol import Symbol
from util import Pos
from atom import Atom

"""
An binary operation atom.
"""
class BinOpAtom(Atom):
    op = None
    l = None
    r = None
    def __init__(self, op, l, r):
        # DEBUG assertions
        assert isinstance(op, basestring)
        assert isinstance(l, Atom)
        assert isinstance(r, Atom)
        
        self.op = op
        self.l = l
        self.r = r

    """
    Override Atom.render().
    """
    def render(self):
        sym = Symbol(self.op)
        return buildHorizBox([self.l.render(), sym, self.r.render()])
                   
