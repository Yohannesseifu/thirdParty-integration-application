<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="fieldUIMetaData">
        <h2 class="jh-entity-heading" data-cy="fieldUIMetaDataDetailsHeading"><span>Field UI Meta Data</span> {{ fieldUIMetaData.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Label</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.label }}</span>
          </dd>
          <dt>
            <span>Input Interface</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.inputInterface }}</span>
          </dd>
          <dt>
            <span>Width</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.width }}</span>
          </dd>
          <dt>
            <span>Note</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.note }}</span>
          </dd>
          <dt>
            <span>Validation Pattern</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.validationPattern }}</span>
          </dd>
          <dt>
            <span>Options</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.options }}</span>
          </dd>
          <dt>
            <span>Display Options</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.displayOptions }}</span>
          </dd>
          <dt>
            <span>Conditions</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.conditions }}</span>
          </dd>
          <dt>
            <span>Translations</span>
          </dt>
          <dd>
            <span>{{ fieldUIMetaData.translations }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="fieldUIMetaData.id"
          :to="{ name: 'FieldUIMetaDataEdit', params: { fieldUIMetaDataId: fieldUIMetaData.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./field-ui-meta-data-details.component.ts"></script>
