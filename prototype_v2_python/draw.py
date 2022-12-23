import pygame

"""
General draw API.
"""
pygame.init()
_screen = pygame.display.set_mode((240,50))
_srf = pygame.Surface(_screen.get_size()).convert()

boxes = []

# TODO: More intelligent blitting-based
def draw():
    _srf.fill((255,255,255))
    for b in boxes:
        b.draw(_srf)
    _screen.blit(_srf, (0,0))
    pygame.display.flip()

def events():
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            return False
    return True


"""
Font API.
"""
pygame.font.init()
_font = pygame.font.SysFont("Comic Sans MS", 36) # TODO: Pick a font with all symbols

def textSize(text):
    return _font.size(text)
def renderFont(char, antialias, color):
    return _font.render(char, antialias, color)
