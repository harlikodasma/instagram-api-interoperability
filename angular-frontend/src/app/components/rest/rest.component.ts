import { Component, OnInit } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatRadioButton, MatRadioGroup } from '@angular/material/radio';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  ValidationErrors,
  Validators
} from '@angular/forms';
import { MatError, MatInput } from '@angular/material/input';
import { NgForOf, NgIf } from '@angular/common';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatButton } from '@angular/material/button';
import { RestService } from '../../services/rest.service';
import { Observable } from 'rxjs';
import { RestOperation } from '../../enum/rest-operation.enum';
import { HttpErrorResponse } from '@angular/common/http';
import { RestForm } from '../../interface/rest-form';
import { SAMPLE_JSON_FOR_REST } from '../../app.constants';

@Component({
  selector: 'app-rest',
  imports: [
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    MatRadioGroup,
    MatRadioButton,
    FormsModule,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput,
    NgIf,
    MatError,
    MatButton,
    NgForOf
  ],
  templateUrl: './rest.component.html',
  styleUrl: './rest.component.scss'
})
export class RestComponent implements OnInit {

  restForm!: FormGroup<RestForm>;
  selectedMethod: RestOperation = RestOperation.GET;
  response: string | null = null;
  responseErrors: string[] | null = null;
  readonly RestOperation: typeof RestOperation = RestOperation;

  constructor(
    private formBuilder: FormBuilder,
    private restService: RestService
  ) {}

  ngOnInit(): void {
    this.restForm = this.formBuilder.group<RestForm>({
      username: this.formBuilder.control('', { validators: Validators.required, nonNullable: true }),
      id: this.formBuilder.control({ value: null, disabled: true }),
      data: this.formBuilder.control({ value: '', disabled: true }, { nonNullable: true })
    }, { validators: this.jsonValidator });
  }

  onMethodChange(): void {
    const idControl: AbstractControl<any, any> | null = this.restForm.get('id');
    const dataControl: AbstractControl<any, any> | null = this.restForm.get('data');

    if (this.selectedMethod === RestOperation.PUT || this.selectedMethod === RestOperation.DELETE) {
      idControl?.enable();
      idControl?.setValidators(Validators.required);
    } else {
      idControl?.disable();
      idControl?.clearValidators();
    }

    if (this.selectedMethod === RestOperation.POST || this.selectedMethod === RestOperation.PUT) {
      dataControl?.enable();
      dataControl?.setValidators(Validators.required);
    } else {
      dataControl?.disable();
      dataControl?.clearValidators();
    }

    idControl?.updateValueAndValidity();
    dataControl?.updateValueAndValidity();
    this.response = null;
  }

  onSubmit(): void {
    const { username, id, data } = this.restForm.value;
    let observable: Observable<any>;

    switch (this.selectedMethod) {
      case RestOperation.GET:
        observable = this.restService.getUser(username!);
        break;
      case RestOperation.POST:
        observable = this.restService.createUser(username!, JSON.parse(data!));
        break;
      case RestOperation.PUT:
        observable = this.restService.updateUser(id!, username!, JSON.parse(data!));
        break;
      case RestOperation.DELETE:
        observable = this.restService.deleteUser(id!, username!);
        break;
      default:
        return;
    }

    observable.subscribe({
      next: (result: any): void => {
        this.responseErrors = null;
        this.response = JSON.stringify(
          this.selectedMethod !== RestOperation.DELETE ? result : JSON.parse('{"status": "ok"}'),
          null,
          2
        );
      },
      error: (error: HttpErrorResponse): void => {
        if (error.status === 400) {
          this.responseErrors = error.error.errors;
        }
      }
    });
  }

  insertSampleJson(): void {
    this.restForm.patchValue({ data: SAMPLE_JSON_FOR_REST });
  }

  private jsonValidator(control: AbstractControl): ValidationErrors | null {
    const form = control as FormGroup;
    const data = form.get('data')?.value;

    if (data && (form.get('data')?.dirty || form.get('data')?.touched)) {
      try {
        JSON.parse(data);
        return null;
      } catch {
        return { json: true };
      }
    }
    return null;
  }
}
