import draw
from box import *
from symbol import Symbol
from util import Pos
from atom import Atom

"""
A variable atom.
"""
class VarAtom(Atom):
    name = None
    def __init__(self, name):
        super(VarAtom, self).__init__()
        self.name = name

    """
    Override Atom.render().
    """
    def render(self):
        sym = Symbol(self.name)
        return buildHorizBox([sym])

    def clone(self):
        return VarAtom(self.name)

    def __str__(self):
        return self.name
