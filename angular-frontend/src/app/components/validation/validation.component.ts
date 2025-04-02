import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCard, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { MatRadioButton, MatRadioGroup } from '@angular/material/radio';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { NgForOf, NgIf } from '@angular/common';
import { ValidationService } from '../../services/validation.service';
import { SAMPLE_XML_FOR_VALIDATION } from '../../app.constants';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-validation',
  imports: [
    MatCard,
    MatCardTitle,
    MatCardHeader,
    MatCardContent,
    ReactiveFormsModule,
    MatRadioGroup,
    MatRadioButton,
    MatFormField,
    MatInput,
    MatButton,
    MatLabel,
    NgIf,
    NgForOf
  ],
  templateUrl: './validation.component.html',
  styleUrl: './validation.component.scss'
})
export class ValidationComponent implements OnInit {

  validationForm!: FormGroup;
  validationSuccess: boolean | null = null;
  validationErrors: string[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {
    this.validationForm = this.formBuilder.group({
      validationType: ['', Validators.required],
      xml: ['', Validators.required]
    });
  }

  validate(): void {
    if (this.validationForm.valid) {
      const { validationType, xml } = this.validationForm.value;
      this.validationService.validate(validationType!, xml!).subscribe({
        next: (): void => {
          this.validationSuccess = true;
        },
        error: (error: HttpErrorResponse): void => {
          if (error.status === 400) {
            this.validationSuccess = false;
            this.validationErrors = error.error.errors;
          }
        }
      });
    }
  }

  insertSampleXml(): void {
    this.validationForm.patchValue({ xml: SAMPLE_XML_FOR_VALIDATION });
  }
}
