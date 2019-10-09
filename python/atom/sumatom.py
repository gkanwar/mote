import draw
from box import *
from symbol import Symbol
from util import Pos
from atom import Atom

"""
A sequence of terms to sum. Sums are always assumed commutative.
"""
class SumAtom(Atom):
    terms = None
    def __init__(self, terms):
        super(SumAtom, self).__init__()
        # DEBUG assertions
        assert isinstance(terms, list)
        assert len(terms) > 1
        self.terms = terms

    """
    Override Atom.render().
    """
    def render(self):
        sym = Symbol('+')
        term_rends = map(lambda t: t.render(), self.terms)
        box_elts = [Symbol("(")]
        for i in range(len(term_rends)-1):
            box_elts.append(term_rends[i])
            box_elts.append(sym)
        box_elts.append(term_rends[-1])
        box_elts.append(Symbol(")"))
        return buildHorizBox(box_elts)

    def clone(self):
        return SumAtom(map(lambda t: t.clone(), self.terms))

    def __str__(self):
        return "(" + " + ".join(map(str, self.terms)) + ")"
