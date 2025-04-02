import { FormControl } from '@angular/forms';

export interface RestForm {
  username: FormControl<string>;
  id: FormControl<number | null>;
  data: FormControl<string>;
}
