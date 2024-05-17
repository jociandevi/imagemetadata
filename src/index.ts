import { registerPlugin } from '@capacitor/core';

import type { ImageMetadataPlugin } from './definitions';

const ImageMetadata = registerPlugin<ImageMetadataPlugin>('ImageMetadata', {
  web: () => import('./web').then(m => new m.ImageMetadataWeb()),
});

export * from './definitions';
export { ImageMetadata };
