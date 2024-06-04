<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="integrationOperation">
        <h2 class="jh-entity-heading" data-cy="integrationOperationDetailsHeading">
          <span>Integration Operation</span> {{ integrationOperation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Operation Type</span>
          </dt>
          <dd>
            <span>{{ integrationOperation.operationType }}</span>
          </dd>
          <dt>
            <span>Third Party Integration</span>
          </dt>
          <dd>
            <div v-if="integrationOperation.thirdPartyIntegration">
              <router-link
                :to="{
                  name: 'ThirdPartyIntegrationView',
                  params: { thirdPartyIntegrationId: integrationOperation.thirdPartyIntegration.id },
                }"
                >{{ integrationOperation.thirdPartyIntegration.id }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span>Operation</span>
          </dt>
          <dd>
            <div v-if="integrationOperation.operation">
              <router-link :to="{ name: 'OperationView', params: { operationId: integrationOperation.operation.id } }">{{
                integrationOperation.operation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="integrationOperation.id"
          :to="{ name: 'IntegrationOperationEdit', params: { integrationOperationId: integrationOperation.id } }"
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

<script lang="ts" src="./integration-operation-details.component.ts"></script>
