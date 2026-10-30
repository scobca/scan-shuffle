import type { App } from 'vue';

import { definePreset, palette } from '@primeuix/themes';
import Aura from '@primeuix/themes/aura';
import PrimeVue from 'primevue/config';
import Tooltip from 'primevue/tooltip';

const ScanShufflePreset = definePreset(Aura, {
  semantic: {
    // palette принимает hex; базовый оттенок совпадает с --color-accent в main.css.
    primary: palette('#6656e8'),
    colorScheme: {
      light: {
        surface: {
          0: 'var(--color-surface)',
          50: 'var(--color-bg)',
          100: 'var(--color-muted-surface)',
          200: 'var(--color-border)',
          300: '#c9ccda',
          400: '#a9aec4',
          500: 'var(--color-text-secondary)',
          600: '#686e89',
          700: '#4d536d',
          800: '#30364d',
          900: 'var(--color-text)',
          950: '#0b0e1c',
        },
        primary: {
          color: 'var(--color-accent)',
          inverseColor: 'var(--color-surface)',
          hoverColor: 'var(--color-accent-hover)',
          activeColor: 'var(--color-accent-hover)',
        },
        formField: {
          background: 'var(--color-surface)',
          borderColor: 'var(--color-border)',
          hoverBorderColor: 'var(--color-accent)',
          focusBorderColor: 'var(--color-accent)',
        },
      },
    },
    focusRing: {
      color: 'var(--color-accent)',
    },
  },
  components: {
    button: {
      root: {
        borderRadius: '0.75rem',
      },
    },
    inputtext: {
      root: {
        borderRadius: '0.75rem',
      },
    },
    tooltip: {
      colorScheme: {
        light: {
          root: {
            background: 'var(--color-text)',
            color: 'var(--color-surface)',
          },
        },
      },
    },
  },
});

export function installPrimeVue(app: App, license: string): void {
  app.use(PrimeVue, {
    license,
    theme: {
      preset: ScanShufflePreset,
      options: { darkModeSelector: '.app-dark' },
    },
  });
  app.directive('tooltip', Tooltip);
}
