"""
Atom describing indexing of contained element.
"""
class IndexAtom(Atom):
    name = None
    inner = None
    upper = None
    def __init__(self, name, inner, upper=False):
        super(IndexAtom, self).__init__()
        self.name = name
        self.inner = inner
        self.upper = upper

    def render(self):
        sym = Symbol(self. name)
        box = buildHorizBox([self.inner.render(), sym])
        box.symbolPos[0]
