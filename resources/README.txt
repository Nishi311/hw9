HW8 Recap:

We were actually able to get basically everything pretty nicely. There are two known issues:

1.) We have no keybindings working. According to the prompt, we did not have to get non-core features
working, so we elected to not implement this in the provider's interactive view.

2.) When exporting the SVG view from the provider's interactive view, exports done mid-animation will
result in the SVG export starting from the same instance. For example, hitting export halfway through
the animation will result in the SVG starting from half-way as well. We believe this is an issue on
the provider's side but as of 14 April, we haven't heard either way.

But all other functions such as speed control, playback control, restarting, looping, and shape
selection are working properly.

The provider views can be accessed via the following "-iv" keywords:

text2, svg2, visual2, provider.

Our views can be accessed via the following "-iv" keywords:

text, svg, visual, interactive.
