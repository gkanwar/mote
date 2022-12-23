import draw
import unicodedata

class Symbol:
    char = None
    def __init__(self, char):
        assert isinstance(char, basestring)
        if (len(char) > 1) and char[0] == '\\':
            try:
                char = unicodedata.lookup(char[1:])
            except KeyError:
                char = char[1:]
        self.char = char

    def draw(self, srf, pos):
        tsrf = draw.renderFont(self.char, True, (0,0,0))
        tpos = tsrf.get_rect()
        tpos.move_ip(pos.x, pos.y)
        srf.blit(tsrf, tpos)

    def getSize(self):
        return draw.textSize(self.char)
