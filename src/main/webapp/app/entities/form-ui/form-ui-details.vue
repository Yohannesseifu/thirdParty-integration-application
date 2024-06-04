<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="formUi">
        <h2 class="jh-entity-heading" data-cy="formUiDetailsHeading"><span>Form Ui</span> {{ formUi.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Form Name</span>
          </dt>
          <dd>
            <span>{{ formUi.formName }}</span>
          </dd>
          <dt>
            <span>Form Description</span>
          </dt>
          <dd>
            <span>{{ formUi.formDescription }}</span>
          </dd>
          <dt>
            <span>Form Type</span>
          </dt>
          <dd>
            <span>{{ formUi.formType }}</span>
          </dd>
          <dt>
            <span>Step Order</span>
          </dt>
          <dd>
            <span>{{ formUi.stepOrder }}</span>
          </dd>
          <dt>
            <span>Third Party Integration</span>
          </dt>
          <dd>
            <div v-if="formUi.thirdPartyIntegration">
              <router-link
                :to="{ name: 'ThirdPartyIntegrationView', params: { thirdPartyIntegrationId: formUi.thirdPartyIntegration.id } }"
                >{{ formUi.thirdPartyIntegration.id }}</router-link
              >
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link v-if="formUi.id" :to="{ name: 'FormUiEdit', params: { formUiId: formUi.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./form-ui-details.component.ts"></script>
