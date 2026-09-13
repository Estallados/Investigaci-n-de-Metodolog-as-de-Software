import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Metodologias } from './metodologias';

describe('Metodologias', () => {
  let component: Metodologias;
  let fixture: ComponentFixture<Metodologias>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Metodologias],
    }).compileComponents();

    fixture = TestBed.createComponent(Metodologias);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
