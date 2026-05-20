---
name: Mediterranean Bohemian
colors:
  surface: '#fefccf'
  surface-dim: '#dedcb1'
  surface-bright: '#fefccf'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f8f6c9'
  surface-container: '#f2f0c4'
  surface-container-high: '#eceabe'
  surface-container-highest: '#e6e5b9'
  on-surface: '#1d1d03'
  on-surface-variant: '#424751'
  inverse-surface: '#323214'
  inverse-on-surface: '#f5f3c7'
  outline: '#727783'
  outline-variant: '#c2c6d3'
  surface-tint: '#175ead'
  primary: '#003e7a'
  on-primary: '#ffffff'
  primary-container: '#0055a4'
  on-primary-container: '#afccff'
  inverse-primary: '#a8c8ff'
  secondary: '#914c08'
  on-secondary: '#ffffff'
  secondary-container: '#fea45d'
  on-secondary-container: '#733a00'
  tertiary: '#004912'
  on-tertiary: '#ffffff'
  tertiary-container: '#00641c'
  on-tertiary-container: '#7ce17e'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#d5e3ff'
  primary-fixed-dim: '#a8c8ff'
  on-primary-fixed: '#001b3c'
  on-primary-fixed-variant: '#004689'
  secondary-fixed: '#ffdcc4'
  secondary-fixed-dim: '#ffb781'
  on-secondary-fixed: '#2f1400'
  on-secondary-fixed-variant: '#703800'
  tertiary-fixed: '#93f993'
  tertiary-fixed-dim: '#77dc7a'
  on-tertiary-fixed: '#002105'
  on-tertiary-fixed-variant: '#005316'
  background: '#fefccf'
  on-background: '#1d1d03'
  surface-variant: '#e6e5b9'
typography:
  headline-xl:
    fontFamily: Playfair Display
    fontSize: 48px
    fontWeight: '700'
    lineHeight: '1.2'
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Playfair Display
    fontSize: 32px
    fontWeight: '700'
    lineHeight: '1.3'
  headline-lg-mobile:
    fontFamily: Playfair Display
    fontSize: 28px
    fontWeight: '700'
    lineHeight: '1.3'
  headline-md:
    fontFamily: Playfair Display
    fontSize: 24px
    fontWeight: '600'
    lineHeight: '1.4'
  body-lg:
    fontFamily: Montserrat
    fontSize: 18px
    fontWeight: '400'
    lineHeight: '1.6'
  body-md:
    fontFamily: Montserrat
    fontSize: 16px
    fontWeight: '400'
    lineHeight: '1.6'
  label-md:
    fontFamily: Montserrat
    fontSize: 14px
    fontWeight: '600'
    lineHeight: '1'
    letterSpacing: 0.05em
  label-sm:
    fontFamily: Montserrat
    fontSize: 12px
    fontWeight: '500'
    lineHeight: '1'
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  unit: 8px
  gutter: 24px
  margin-mobile: 20px
  margin-desktop: 64px
  section-gap: 80px
---

## Brand & Style

The design system is crafted to evoke the spirit of Tangier—the "Bride of the North"—where the Mediterranean meets the Atlantic and historic heritage meets modern wanderlust. The personality is "Mediterranean Bohemian": a sophisticated blend of sun-drenched warmth, artisanal textures, and contemporary clarity.

The UI targets global travelers seeking authentic cultural experiences. It achieves an emotional response of serenity and discovery by balancing heavy, traditional motifs with airy, modern layouts. The style utilizes **Minimalism** as a base for structure, layered with **Tactile** Moroccan elements like zellige patterns and high-quality photography of the Kasbah and coastline to ground the digital experience in a physical sense of place.

## Colors

The palette is a direct reflection of the Tangier landscape. **Moroccan Blue** serves as the primary anchor, used for core branding and primary actions to evoke the deep sea and the painted walls of the medina. **Sunset Terracotta** provides a warm secondary contrast, ideal for highlighting movement, calls to action, or evening-related content.

**Mint Green** and **Cream** act as decorative accents, used sparingly to suggest the freshness of Moroccan tea and the architectural heritage of the city. The background is a **Warm Off-White**, which reduces the harshness of pure white and provides a paper-like, editorial quality to the interface.

## Typography

This design system employs a classic serif/sans-serif pairing to distinguish between narrative and utility. **Playfair Display** is used for headlines to provide a premium, literary feel that honors the history of Tangier’s international zone. **Montserrat** is used for all functional and body text, ensuring high readability and a clean, geometric contrast to the ornate headlines.

For mobile screens, headlines scale down slightly to maintain balanced proportions within narrow viewports. Labels use Montserrat with increased letter spacing and uppercase styling to provide clear hierarchy in navigation and metadata without competing with primary headings.

## Layout & Spacing

The layout follows a **fluid grid** model with a focus on generous white space to mimic the openness of the Mediterranean horizon. On desktop, a 12-column grid is used with wide 64px margins to keep content centered and focused. On mobile, the margins tighten to 20px to maximize real estate for photography.

Spacing follows an 8px incremental scale. Significant vertical gaps (80px+) are used between sections to allow the brand's geometric patterns and high-quality imagery to "breathe." Content should reflow vertically on mobile, moving from multi-column image galleries to single-column featured cards.

## Elevation & Depth

Visual hierarchy is achieved through **Tonal Layers** and **Ambient Shadows**. Instead of harsh black shadows, this design system uses soft, diffused shadows with a slight Terracotta or Blue tint (#E38E49 at 10% opacity) to maintain warmth.

- **Level 1 (Base):** Content cards resting on the background with a subtle 1px Cream border.
- **Level 2 (Hover/Active):** Cards lifted with a soft shadow (Y: 4px, Blur: 12px) to indicate interactivity.
- **Level 3 (Overlays):** Modals and navigation menus using a backdrop blur (Glassmorphism) to keep the sense of place visible even when focused on a specific task.

Subtle Zellige patterns are applied to the lowest elevation layer (background) or as high-contrast masks for image containers to create architectural depth.

## Shapes

The shape language is defined by **Rounded** geometry (16px base radius), reflecting the arched doorways and organic masonry found in the Kasbah. 

- **Standard Elements:** Buttons and small inputs use a 0.5rem (8px) radius.
- **Container Elements:** Cards, image frames, and bottom sheets use a 1rem (16px) radius to create a soft, inviting aesthetic.
- **Large Sections:** Large featured image containers or call-to-action blocks use a 1.5rem (24px) radius on top corners only to mimic traditional Moroccan archways.

## Components

### Buttons
Primary buttons are solid Moroccan Blue with white Montserrat text. They feature a slight 2px bottom "shadow" border in a darker shade of blue rather than a glow. Secondary buttons use the Terracotta hue for high-visibility tourist actions like "Book Now."

### Cards
Cards are the primary vehicle for discovery. They feature a 16px corner radius and "full-bleed" photography. Captions are placed on a Cream background at the bottom of the card, using Playfair Display for titles.

### Input Fields
Search bars and text inputs use a Warm Off-White fill with a subtle 1px Cream border. The focus state transitions the border to Moroccan Blue.

### Zellige Accents
Specialized components (like "Did You Know?" tips) feature a subtle geometric tile pattern as a background mask or a thin 4px decorative top border.

### Navigation
A bottom navigation bar on mobile uses Mint Green for active states, providing a refreshing visual cue that contrasts with the primary blue. On desktop, the navigation is transparent until scroll, transitioning to a semi-opaque Cream with a backdrop blur.