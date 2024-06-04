<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="field">
        <h2 class="jh-entity-heading" data-cy="fieldDetailsHeading"><span>Field</span> {{ field.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Name</span>
          </dt>
          <dd>
            <span>{{ field.name }}</span>
          </dd>
          <dt>
            <span>Data Type</span>
          </dt>
          <dd>
            <span>{{ field.dataType }}</span>
          </dd>
          <dt>
            <span>Is Unique</span>
          </dt>
          <dd>
            <span>{{ field.isUnique }}</span>
          </dd>
          <dt>
            <span>Max Length</span>
          </dt>
          <dd>
            <span>{{ field.maxLength }}</span>
          </dd>
          <dt>
            <span>Min Length</span>
          </dt>
          <dd>
            <span>{{ field.minLength }}</span>
          </dd>
          <dt>
            <span>Min Value</span>
          </dt>
          <dd>
            <span>{{ field.minValue }}</span>
          </dd>
          <dt>
            <span>Max Value</span>
          </dt>
          <dd>
            <span>{{ field.maxValue }}</span>
          </dd>
          <dt>
            <span>Is Required</span>
          </dt>
          <dd>
            <span>{{ field.isRequired }}</span>
          </dd>
          <dt>
            <span>Sort Order</span>
          </dt>
          <dd>
            <span>{{ field.sortOrder }}</span>
          </dd>
          <dt>
            <span>Form Ui</span>
          </dt>
          <dd>
            <div v-if="field.formUi">
              <router-link :to="{ name: 'FormUiView', params: { formUiId: field.formUi.id } }">{{ field.formUi.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Field UI Meta Data</span>
          </dt>
          <dd>
            <div v-if="field.fieldUIMetaData">
              <router-link :to="{ name: 'FieldUIMetaDataView', params: { fieldUIMetaDataId: field.fieldUIMetaData.id } }">{{
                field.fieldUIMetaData.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link v-if="field.id" :to="{ name: 'FieldEdit', params: { fieldId: field.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./field-details.component.ts"></script>
